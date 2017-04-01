(function() {
	angular.module("hyperactive.angular.utils", ['ui.bootstrap']).directive("dateBefore", function(uibDateParser) {
		return {
			require : 'ngModel',
			link : function(scope, el, attrs, ctrl) {
				var isInclusive = attrs.dateOrEquals ? scope
						.$eval(attrs.dateOrEquals) : false, validate = function(
						val1, val2) {
					if (val1 === undefined
							|| val2 === undefined)
						return;
					var isArray = val2 instanceof Array;
					var isValid = true;
					var date1 = uibDateParser.parse(val1, "dd/MM/yyyy", new Date());
					if (isArray && val2.length > 0) {
						for (var i = 0; i < val2.length; i++) {
							if (val2[i] !== undefined) {
								var date2 = uibDateParser.parse(val2[i],"dd/MM/yyyy",new Date());
								isValid = isValid && (isInclusive ? date1 <= date2 : date1 < date2);
							}
							if (!isValid)
								break;
						}
					} else {
						if (val2 !== undefined) {
							var date2 = uibDateParser.parse(val2, "dd/MM/yyyy",new Date());
							isValid = isInclusive ? date1 <= date2 : date1 < date2;
						}
					}
					ctrl.$setValidity('dateBefore', isValid);
				};
				scope.$watch(attrs.dateBefore, function() {
					validate(ctrl.$viewValue, scope.$eval(attrs.dateBefore));
				});

				ctrl.$parsers.unshift(function(value) {
					validate(value, scope
							.$eval(attrs.dateBefore));
					return value;
				})
			}
		};
	});

	angular.module("hyperactive.angular.utils").directive("dateAfter", function(uibDateParser) {
		return {
			require : 'ngModel',
			link : function(scope, el, attrs, ctrl) {
				var isInclusive = attrs.dateOrEquals ? scope
						.$eval(attrs.dateOrEquals) : false, validate = function(
						val1, val2) {
					if (val1 === undefined || val2 === undefined)
						return;
					var isArray = val2 instanceof Array;
					var isValid = true;
					var date1 = uibDateParser.parse(val1, "dd/MM/yyyy", new Date());
					if (isArray && val2.length > 0) {
						for (var i = 0; i < val2.length; i++) {
							if (val2[i] !== undefined) {
								var date2 = uibDateParser.parse(val2[i],"dd/MM/yyyy",new Date());
								isValid = isValid && (isInclusive ? date1 >= date2 : date1 > date2);
							}
							if (!isValid)
								break;
						}
					} else {
						if (val2 !== undefined) {
							var date2 = uibDateParser.parse( val2, "dd/MM/yyyy", new Date());
							isValid = isInclusive ? date1 >= date2 : date1 > date2;
						}
					}
					ctrl.$setValidity('dateAfter', isValid);
				};
				scope.$watch(attrs.dateAfter, function() {
					validate(ctrl.$viewValue, scope
							.$eval(attrs.dateAfter));
				});

				ctrl.$parsers.unshift(function(value) {
					validate(value, scope.$eval(attrs.dateAfter));
					return value;
				})
			}
		};
	});
})();
