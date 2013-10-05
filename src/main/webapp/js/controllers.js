/**
 * Renderer controller
 * @param $scope the model scope
 * @param rendererService the renderer service
 */
function RendererCtrl($scope, rendererService) {
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

/**
 * Wordifier controller
 * @param $scope the model scope
 * @param wordifierService the wordifier service
 */
function WordifierCtrl($scope, wordifierService) {
	$scope.wordifiers = [];

	/**
	 * Formats a word sequence
	 * @param sequence word sequence
	 * @returns {*}
	 */
	$scope.formatWordSequence = function(sequence) {
		return 'xxx';
	};

	/**
	 * Updates the wordifications for the given wordifier
	 * @param wordifier the wordifier
	 */
	$scope.updateWordification = function(wordifier) {
		var number = parseInt($scope.number);

		if (!isNaN(number)) {
			wordifierService.wordify(wordifier, $scope.number, function(response) {
				wordifier.wordification = response;
			});
		} else {
			wordifier.wordification = '';
		}
	};

	/**
	 * Updates all wordifications
	 */
	$scope.updateAllWordifications = function() {
		for (var w = 0; w < $scope.wordifiers.length; w++) {
			$scope.updateWordification($scope.wordifiers[w]);
		}
	};

	/**
	 * Initialise the list of wordifiers...
	 */
	wordifierService.getAll(function(response) {
		$scope.wordifiers = response;
	});
}