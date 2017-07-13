app.factory('suffix', function() {

    var thisIsPrivate = "Private";

    function getPrivate() {

        return thisIsPrivate;

    }


    return {
        user : "rest/users",
        currentUser : "rest/users/currentUser",
        userlist: "rest/users/list",
        getTenants:"rest/users/getTenant",
        policy: "rest/policy",
        controller: "rest/policy/controller",
        controllerAll: "rest/policy/getAllController",
        netNode: "rest/policy/netNode",
        netNodeAll: "rest/policy/getAllNetNode",
        cleanDevAll: "rest/policy/getAllCleanDev",
        po: "rest/policy/po",
        backUp:"rest/sys/backUp",
        cleanDev:"rest/policy/cleanDev",
        cleanDevAll: "rest/policy/getAllCleanDev",
        getPrivate: "getPrivate"

    };

});