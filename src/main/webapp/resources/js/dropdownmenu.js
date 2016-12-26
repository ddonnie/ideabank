var dropdown = function() {



//User drop down menu

	$('#button_user').click(function() {
  		$('.dropdown_user_menu').toggle();
 	 });
  


//Sorting drop down menu

	$('.wrap_for_sorting').click(function() {
  		$('.dropdown-menu').toggle();
 	 });
  
};



$(document).ready(dropdown);


