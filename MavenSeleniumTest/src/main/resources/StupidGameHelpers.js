if(typeof String.prototype.trim !== 'function') {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g, ''); 
    }
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}

function harvestAll_OLD(){
    $.ajax({
        type: "POST", 
        url: "http://www.sexgangsters.com/api/", 
        "data": {
            "data": "{\"method\":\"city.business.collectAll\"}"
        }, 
        headers: {
            "X-CSRFToken":getCookie("csrftoken")
        }
    });
}

function getRivalsList_OLD(){
    var returnVar = "DTC";
    $.ajax({
        type:       "POST", 
        url:        "http://www.sexgangsters.com/api/", 
        async:      false,
        "data":     {
            "data": "{\"method\":\"pvp.rivals.get\",\"args\":{\"cSetId\":0,\"cItemId\":0}}"
        }, 
        headers:    {
            "X-CSRFToken":getCookie("csrftoken")
        },
        success:    function(data) {
            returnVar=data;
        }
    });
    return returnVar;
}

function makeFightStep1_OLD(rivalId){
    var returnVar = "DTC";
    var dataVar = "{\"method\":\"pvp.fight.start\",\"args\":{\"rivalId\":" + rivalId +"}}";
    $.ajax({
        type:       "POST", 
        url:        "http://www.sexgangsters.com/api/", 
        async:      false,
        "data":     {
            "data": dataVar
        }, 
        headers:    {
            "X-CSRFToken":getCookie("csrftoken")
        },
        success:    function(data) {
            returnVar = data;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
            returnVar = textStatus;
        }    
        
    });
    makeFightStep2();
    return returnVar;
}

function makeFightStep2_OLD(){
    var returnVar = "DTC";
    // Fight exec
    $.ajax({
        type:       "POST", 
        url:        "http://www.sexgangsters.com/api/", 
        async:      false,
        "data":     {
            "data": "{\"method\":\"pvp.fight.finish\",\"args\":{\"boosters\":null}}"
        }, 
        headers:    {
            "X-CSRFToken":getCookie("csrftoken")
        },
        success:    function(data) {
            returnVar=data;
        }
    }); 
    return returnVar;
}


function testFunction() {
    return "hello from js";
}

function genericStupidGameCaller(dataString){
    var returnVar = "DTC";
    // Fight exec
    $.ajax({
        type:       "POST", 
        url:        "http://www.sexgangsters.com/api/", 
        async:      false,
        "data":     {
            "data": dataString
        }, 
        headers:    {
            "X-CSRFToken":getCookie("csrftoken")
        },
        success:    function(data) {
            returnVar=data;
        }
    }); 
    return returnVar;
}

var STUPID_GAME_HARVESTALL = {
    "method":"city.business.collectAll"
};

function harvestAll(){
    return genericStupidGameCaller("{\"method\":\"city.business.collectAll\"}");
}

function getRivalsList(){
    return genericStupidGameCaller("{\"method\":\"pvp.rivals.get\",\"args\":{\"cSetId\":0,\"cItemId\":0}}");
}

function fightStep1(rivalId){
    var dataVar = "{\"method\":\"pvp.fight.start\",\"args\":{\"rivalId\":" + rivalId +"}}";
    return genericStupidGameCaller(dataVar);
}

function fightStep2(){
    return genericStupidGameCaller("{\"method\":\"pvp.fight.finish\",\"args\":{\"boosters\":null}}");
}

function fightAll(rivalId){ // Sambe behavior as before
    var step1Return = fightStep1(rivalId);
    var step2Return = fightStep2();
    return {
        "step1" :step1Return, 
        "step2": step2Return
    };
}