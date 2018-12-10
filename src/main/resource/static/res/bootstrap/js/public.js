$(function() {
	$
			.ajax({
				url : "/bizmgt/main/leftPage?t=t",
				data : {},
				type : "post",
				dataType : "json",
				cache : false,
				success : function(data) {
					var innerHtml = "";
					if (data && data.data.length > 0) {
						for (var i = 0; i < data.data.length; i++) {
							var row = data.data[i];
							var children = row.children;
							innerHtml = innerHtml + '<li>';
							innerHtml = innerHtml
									+ '<a  onclick = "clickNav(this.parentNode)" href="#">';
							innerHtml = innerHtml
									+ '<i class="fa fa-wrench fa-fw"></i>'
									+ row.text
									+ '<span class="fa arrow"></span></a>'
							if (children && children.length > 0) {
								innerHtml = innerHtml
										+ '<ul class="nav nav-second-level collapse" >';
								for (var j = 0; j < children.length; j++) {
									if (children[j].children.length > 0) {
										innerHtml = innerHtml + '<li>';
										var url = window.location.href;
										innerHtml = innerHtml
												+ '<a onclick = "clickNavSecond(this.parentNode)" href="javascript:void(0)">'
												+ children[j].text
												+ '<span class="fa arrow"></span></a>';
										innerHtml = innerHtml
												+ '<ul class="nav nav-third-level collapse" >';
										for (var k = 0; k < children[j].children.length; k++) {
											innerHtml = innerHtml + '<li>';
											if (url
													.endsWith(children[j].children[k].href)) {
												innerHtml = innerHtml
														+ '<a class="active" href="'
														+ children[j].children[k].href
														+ '">'
														+ children[j].children[k].text
														+ '</a>';
											} else {
												innerHtml = innerHtml
														+ '<a href="'
														+ children[j].children[k].href
														+ '">'
														+ children[j].children[k].text
														+ '</a>';
											}
											innerHtml = innerHtml + '</li>';
										}
										innerHtml = innerHtml + '</ul>';
										innerHtml = innerHtml + '</li>';
									} else {
										innerHtml = innerHtml + '<li>';
										var url = window.location.href;
										if (url.endsWith(children[j].href)) {
											innerHtml = innerHtml
													+ '<a class="active" href="'
													+ children[j].href + '">'
													+ children[j].text + '</a>';
										} else {
											innerHtml = innerHtml + '<a href="'
													+ children[j].href + '">'
													+ children[j].text + '</a>';
										}
										innerHtml = innerHtml + '</li>';
									}
								}
								innerHtml = innerHtml + '</ul>';
							}
							innerHtml = innerHtml + '</li>';
						}
					}
					$('#side-menu').append(innerHtml);
					$("#side-menu").find(".active").parent().parent().addClass(
							"in");
					$("#side-menu").find(".active").parent().parent().parent()
							.parent().addClass("in");
				}
			});
});

function clickNavSecond(obj) {
	var ul = $(obj).find("ul");
	if ($(obj).hasClass("active")) {
		$(obj).removeClass("active");
		$(ul[0]).removeClass("in");
	} else {
		$(obj).addClass("active");
		$(ul[0]).addClass("in");
	}
}
function clickNav(obj) {
	var ul = $(obj).find("ul");

	if ($(obj).hasClass("active")) {
		$(obj).removeClass("active");
		$(ul[0]).removeClass("in");
	} else {
		$(obj).addClass("active");
		$(ul[0]).addClass("in");
	}
}