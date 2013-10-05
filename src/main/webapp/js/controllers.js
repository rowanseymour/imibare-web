/**
 * Controller main page
 * @param $scope the model scope
 * @param rendererService the renderer service
 */
function ImibareCtrl($scope, rendererService) {
	$scope.renderers = [];

	/**
	 * Formats a noun classification
	 * @param classification the noun classification
	 * @returns {*}
	 */
	$scope.formatNounClassification = function(classification) {
		if (classification.nounClass) {
			return 'Class ' + classification.nounClass;
		} else {
			return classification.gender;
		}
	};

	/**
	 * Updates the number rendering for the given renderer
	 * @param renderer the renderer
	 */
	$scope.updateRendering = function(renderer) {
		var number = parseInt($scope.number);
		var selectedClassification = renderer.selectedClassification;

		if (!isNaN(number)) {
			rendererService.render(renderer, $scope.number, selectedClassification, function(response) {
				renderer.rendering = response;
			});
		} else {
			renderer.rendering = '';
		}
	};

	/**
	 * Updates all rendererings for all renderers
	 */
	$scope.updateAllRenderings = function() {
		for (var r = 0; r < $scope.renderers.length; r++) {
			$scope.updateRendering($scope.renderers[r]);
		}
	};

	/**
	 * Initialise the list of renderers...
	 */
	rendererService.getAll(function(response) {
		$scope.renderers = response;

		$scope.updateAllRenderings();
	});
}