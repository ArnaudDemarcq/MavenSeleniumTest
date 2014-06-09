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

function harvestAll(){
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

function getRivalsList(){
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

function makeFightStep1(rivalId){
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

function makeFightStep2(){
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

function harvestAll_test(){
    return genericStupidGameCaller("{\"method\":\"city.business.collectAll\"}");
}