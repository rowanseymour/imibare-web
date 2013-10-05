var app = angular.module('imibare', ['ngResource']);

/**
 * The renderer service
 */
app.factory('rendererService', function ($resource, $http) {
	return {
		/**
		 * Gets all available renderers
		 * @param callback called when request is complete
		 */
		getAll: function(callback) {
			var api = $resource('rest/renderers');

			api.query(function(response) {
				callback(response);
			});
		},
		/**
		 * Renders the given number
		 * @param renderer the renderer
		 * @param number the number
		 * @param classification
		 * @param callback called when request is complete
		 */
		render: function(renderer, number, classification, callback) {
			var url = 'rest/renderers/' + renderer.locale + '/' + number;

			if (classification) {
				url += '/' + classification.code;
			}

			$http({method: 'GET', url: url})
			.success(function(response){
				callback(response);
			});
		}
	}
});