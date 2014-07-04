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

// Make sure enougth equipment is purchased to proceeed to the quests
// Exact formula is not known for the amount. Strangely, userData.items does contain the correct Id ...
// But NOT directly the amount. It it not Amout*time either, even if it seems so for most items
// So we'll have to store the target "fake count" number. There is still to be understood here ...
var targetItemCountList = [
{
    "id": 52,
    "targetQuantity": 100, 
    "lvl": 1
},{
    "id": 62,
    "targetQuantity": 75, 
    "lvl": 1
}
];

function getItemQuantity(itemId){
    var itemList = userData.items;
    for (var i = 0 ; i< itemList.length ; i++){
        var currentItem = userData.items[i];
        var currentItemId = currentItem[0];
        var currentItemCount = currentItem[1];
        if (currentItemId == itemId) {
            return currentItemCount;
        }
    }
    return -1;
}

var SUPID_GAME_BUY_ITEM_RAW ={
    "method":"user.items.buy",
    "args":{
        "typeId":52,
        "count":1
    }
};

function buyAllRequiredItems(){
    var currentLevel = 45
    for (var i = 0 ; i< targetItemCountList.length ; i ++){
        var currentTargetItem = targetItemCountList[i];
        if (currentTargetItem.lvl <= currentLevel){ // Only purchassable items
            var realQuantity = getItemQuantity(currentTargetItem.id);
            if (realQuantity < currentTargetItem.targetQuantity){ 
                var buy_order = SUPID_GAME_BUY_ITEM_RAW;
                buy_order.args.typeId = currentTargetItem.id;
                return genericStupidGameCaller(JSON.stringify(buy_order));
            }
        }
    }
    return tmpReturn =  {
        "cause":"Nothing to buy"
    };
}