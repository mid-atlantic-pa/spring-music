angular.module('info', ['ngResource']).factory('Info', function ($resource) {
    return $resource('appinfo');
});

function InfoController($scope, Info, $location, $window, $interval) {
    $scope.info = Info.get();

    $scope.info.$promise.then(function () {
        if ($scope.info.color.value == "blue") {
            $scope.isBlueColor = true;
        } else {
            $scope.isBlueColor = false;
        }
    });

    console.info($location.search().refresh);
    if ($location.search().refresh) {
        $scope.refreshEnabled = true;
        $interval(function () {
            $window.location.reload();
        }, 2000);
    } else {
        $scope.refreshEnabled = false;
    }


    $scope.toggleRefreshStatus = function () {
        if ($scope.refreshEnabled) {
            $window.location.href = $window.location.origin;
        } else {
            $window.location.href = $window.location.origin + "/?refresh";
        }
    }
}
