var gameModel;
var alerted = false;

//This function will be called once the page is loaded.  It will get a new game model from the back end, and display it.
$( document ).ready(function() {


     var audio = new Audio('audio/Music.mp3');
        audio.play();


    $.getJSON("model", function (json) {
        displayGameState(json);
        gameModel = json;
    });

    $('#TheirBoard').on("click", "td", function (event) {
        var coords = this.id.split("_");
        fire(coords[0], coords[1]);
    });

    $('#TheirBoard').on("contextmenu", "td", function (event) {
        var coords = this.id.split("_");
        scan(coords[0], coords[1]);
    });
});

function placeShip() {
   // This ajax call will asynchronously call the back end, and tell it where to place the ship, then get back a game model with the ship placed, and display the new model.
   var request = $.ajax({
     url: "/placeShip/"+$( "#shipSelec" ).val()+"/"+$( "#rowSelec" ).val()+"/"+$( "#colSelec" ).val()+"/"+$( "#orientationSelec" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   //This will be called when the call is returned from the server.
   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   // if there is a problem, and the back end does not respond, then an alert will be shown.
   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });
};

//Similar to placeShip, but instead it will fire at a location the user selects.
function fire(row, column) {
    if (!row) {
        row = $( "#colFire" ).val();
    }
    if (!column) {
        column = $( "#rowFire" ).val();
    }
    var request = $.ajax({
        url: "/fire/"+column+"/"+row,
        method: "post",
        data: JSON.stringify(gameModel),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    });


    request.done(function( currModel ) {
        displayGameState(currModel);
        gameModel = currModel;

        setTimeout( function(){
            for (var i = 0; i < gameModel.compShips.length; i++){
                if (gameModel.compShips[i].isSunk == true) {
                    document.getElementById(gameModel.compShips[i].name).setAttribute("class", "hidden");
                }
            }

            if (gameModel.gameOver){
                if(!alerted){
                     alert("Game Over.\nRefresh to play again.");
                     alerted = true;
                     }
                }
        }, 50);

    });

    request.fail(function( jqXHR, textStatus ) {
        alert( "Request failed: " + textStatus );
    });

}

// Perform a scan at the specified coordintes
function scan(row, column) {
    var request = $.ajax({
        url: "/scan/"+row+"/"+column,
        method: "post",
        data: JSON.stringify(gameModel),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    });

    request.done(function (currModel) {
        gameModel = currModel;
        displayGameState(gameModel);
        scanAnimation(row, column, gameModel.scanResult);
        displayGameState(gameModel);
    });

    request.fail(function( jqXHR, textStatus) {
        alert( "Request failed: " + textStatus);
    })
}

// Displays an animated plus and colored results after a scan
function scanAnimation(row, column, scanResult) {
    setTimeout(function() {
        $("#TheirBoard #" + row + "_" + column).css("background-color", "Gold");
        setTimeout(function() {
            $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
            row++;
            $("#TheirBoard #" + row + "_" + column).css("background-color", "Gold");
            row -= 2;
            $("#TheirBoard #" + row + "_" + column).css("background-color", "Gold");
            row++;
            column++;
            $("#TheirBoard #" + row + "_" + column).css("background-color", "Gold");
            column -= 2;
            $("#TheirBoard #" + row + "_" + column).css("background-color", "Gold");
            column++;
            setTimeout(function() {
                row++;
                $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
                row -= 2;
                $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
                row += 1;
                column++;
                $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
                column -= 2;
                $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
                column++;

                if (scanResult)
                    scanBlinkAnimation(row, column, "red", 3);
                else
                    scanBlinkAnimation(row, column, "white", 3);


            }, 200);
        }, 200);
    }, 200);
}

// The second half of the scan animation, blinks a color multiple times
function scanBlinkAnimation(row, column, color, repeat) {
    if (repeat > 0) {
        setTimeout(function() {
        $("#TheirBoard #" + row + "_" + column).css("background-color", color);
        row++;
        $("#TheirBoard #" + row + "_" + column).css("background-color", color);
        row -= 2;
        $("#TheirBoard #" + row + "_" + column).css("background-color", color);
        row++;
        column++;
        $("#TheirBoard #" + row + "_" + column).css("background-color", color);
        column -= 2;
        $("#TheirBoard #" + row + "_" + column).css("background-color", color);
        column++;
        setTimeout(function () {
            $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
            row++;
            $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
            row -= 2;
            $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
            row++;
            column++;
            $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
            column -= 2;
            $("#TheirBoard #" + row + "_" + column).css("background-color", getTileColor(row, column));
            column++;
            scanBlinkAnimation(row, column, color, repeat - 1)
        }, 500);
        }, 200);
    }
}

//This function will display the game model.  It displays the ships on the users board, and then shows where there have been hits and misses on both boards.
function displayGameState(gameModel){
$( '#MyBoard td'  ).css("background-color", "DarkBlue");
$( '#TheirBoard td'  ).css("background-color", "DarkBlue");

displayShip(gameModel.aircraftCarrier);
displayShip(gameModel.battleship);
displayShip(gameModel.cruiser);
displayShip(gameModel.destroyer);
displayShip(gameModel.submarine);

for (var i = 0; i < gameModel.computerMisses.length; i++) {
   $( '#TheirBoard #' + gameModel.computerMisses[i].Down + '_' + gameModel.computerMisses[i].Across ).css("background-color", "green");
}
for (var i = 0; i < gameModel.computerHits.length; i++) {
   $( '#TheirBoard #' + gameModel.computerHits[i].Down + '_' + gameModel.computerHits[i].Across ).css("background-color", "red");
}
for (var i = 0; i < gameModel.playerMisses.length; i++) {
   $( '#MyBoard #' + gameModel.playerMisses[i].Down + '_' + gameModel.playerMisses[i].Across ).css("background-color", "green");
}
for (var i = 0; i < gameModel.playerHits.length; i++) {
   $( '#MyBoard #' + gameModel.playerHits[i].Down + '_' + gameModel.playerHits[i].Across ).css("background-color", "red");
}
}

// Gets an individual tile's intended color (for the computer's board only)
// Used for the scan animations
function getTileColor(row, column) {
    for (var i = 0; i < gameModel.computerMisses.length; i++) {
        if (gameModel.computerMisses[i].Down == row && gameModel.computerMisses[i].Across == column)
            return "green";
    }
    for (var i = 0; i < gameModel.computerHits.length; i++) {
        if (gameModel.computerHits[i].Down == row && gameModel.computerHits[i].Across == column)
            return "red";
    }
    return "DarkBlue";
}


//This function will display a ship given a ship object in JSON
function displayShip(ship){
 startCoordAcross = ship.start.Across;
 startCoordDown = ship.start.Down;
 endCoordAcross = ship.end.Across;
 endCoordDown = ship.end.Down;
 if(startCoordAcross > 0){
    if(startCoordAcross == endCoordAcross){
        for (i = startCoordDown; i <= endCoordDown; i++) {
            $( '#MyBoard #'+i+'_'+startCoordAcross  ).css("background-color", "slategrey");
        }
    } else {
        for (i = startCoordAcross; i <= endCoordAcross; i++) {
            $( '#MyBoard #'+startCoordDown+'_'+i  ).css("background-color", "slategrey");
        }
    }
 }
}
