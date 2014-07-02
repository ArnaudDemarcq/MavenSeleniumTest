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
var STUPID_GAME_GET_RIVALS_RAW = {
    "method":"pvp.rivals.get",
    "args":{
        "cSetId":0,
        "cItemId":0
    }
};

function getRivalsList(){
    var tmpGetRivals = STUPID_GAME_GET_RIVALS_RAW;
    for (var i = 0; i < userData.collectibles.length; i++) {
        var currentCityColl = userData.collectibles[i];
        if (! currentCityColl.collected){
            tmpGetRivals.args.cSetId = currentCityColl.id;
            for (var j = 0 ; j < currentCityColl.items.length; j++){
                var currentItem = currentCityColl.items[j];
                if (currentItem.qty < 1 ){
                    tmpGetRivals.args.cItemId = currentItem.id;
                }
            }
        }
    }
    return genericStupidGameCaller(JSON.stringify(tmpGetRivals));
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

var STUPID_GAME_DO_JOB_RAW = {
    "method":"city.loc.job.do",
    "args":{
        "cityId":6,
        "locId":602
    }
};

function doJob(){
    if (userData.resources.energy > 15) {
        var tmpDoJob = STUPID_GAME_DO_JOB_RAW;
        tmpDoJob.args.cityId = userData.cities[0].id;
        tmpDoJob.args.locId = 100 * userData.cities[0].id + userData.cities[0].stage+1;
        return genericStupidGameCaller(JSON.stringify(tmpDoJob));
    }
    return tmpReturn =  {
        "cause":"Not Enought Energy"
    };
}

var STUPID_GAME_BUY_BUSINESS ={
    "method":"city.business.buy",
    "args":{
        "cityId":7,
        "businessId":702
    }
};
var STUPID_GAME_UPGRADE_BUSINESS ={
    "method":"city.business.upgrade",
    "args":{
        "cityId":7,
        "businessId":702
    }
};