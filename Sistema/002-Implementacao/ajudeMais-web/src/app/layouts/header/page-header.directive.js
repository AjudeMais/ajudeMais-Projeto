(function() {
	angular.module('layout').directive('pageHeader', function() {
		return {
			template : '<section class="content-header"><h1 >{{pageTitle}}</h1></section>',
			restrict : 'E',
			replace : true,
		}
	});
})();