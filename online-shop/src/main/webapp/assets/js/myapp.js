$(function(){
    // solving the active menu problem
    switch(menu){
        
        case 'About us':
            $('#about').addClass('active');
            break;
        case 'Contact us':
            $('#contact').addClass('active');
            break;
        case 'All products':
            $('#productList').addClass('active');
            break;  
        default:
            $('#productList').addClass('active');
            $('#a_' + menu).addClass('active');
            break;
    }
});