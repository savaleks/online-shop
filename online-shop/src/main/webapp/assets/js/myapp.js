$(function () {
    // solving the active menu problem
    switch (menu) {

        case 'About us':
            $('#about').addClass('active');
            break;
        case 'Contact us':
            $('#contact').addClass('active');
            break;
        case 'All products':
            $('#productList').addClass('active');
            break;
        case 'Manage products':
            $('#manageProducts').addClass('active');
            break;
        default:
            if (menu == "Home") break;
            $('#productList').addClass('active');
            $('#a_' + menu).addClass('active');
            break;
    }

    // to tackle csrf token
    var token = $('meta[name="_csrf"]').attr('content');
    var header = $('meta[name="_csrf_header"]').attr('content');

    if(token.length>0 && header.length>0){
        // token header for the ajax request
        $(document).ajaxSend(function(e, xhr, options){
            xhr.setRequestHeader(header, token);
        });
    }

    // code for jquery dataTable
    //create a dataset


    var $table = $('#productListTable');

    // execute the below code only where we have this table
    if ($table.length) {
        //console.log('Inside the table.');

        var jsonUrl = '';
        if (window.categoryId == '') {
            jsonUrl = window.contextRoot + '/json/data/all/products';
        } else {
            jsonUrl = window.contextRoot + '/json/data/category/' +
                window.categoryId + '/products';
        }

        $table.DataTable({
            lengthMenu: [
                [3, 5, 10, -1],
                ['3 Records', '5 Records', '10 Records', 'ALL']
            ],
            pageLength: 5,


            ajax: {
                url: jsonUrl,
                dataSrc: ''
            },
            columns: [

                {
                    data: 'code',
                    mRender: function (data, type, row) {
                        return '<img src="' + window.contextRoot + '/resources/images/' + data + '.jpg" style="width:100px;height:100px;"/>';
                    }
                },
                {
                    data: 'name'
                },
                {
                    data: 'brand'
                },
                {
                    data: 'unitPrice',
                    mRender: function (data, type, row) {
                        return '&euro; ' + data
                    }
                },
                {
                    data: 'quantity',
                    mRender: function (data, type, row) {
                        if (data < 1) {
                            return '<span style="color:red">Out of Stock</span>';
                        }
                        return data;
                    }
                },
                {
                    data: 'id',
                    bSortable: false,
                    mRender: function (data, type, row) {
                        var str = '';
                        str += '<a href="' + window.contextRoot + '/show/' + data + '/product" class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span></span></a> &#160;';

                        if(userRole == 'ADMIN'){
                            str += '<a href="' + window.contextRoot + '/manage/' + data + '/product" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span></span></a>';
                        } else {

                            if (row.quantity < 1) {
                                str += '<a href="javascript:void(0)" class="btn btn-success disabled"><span class="glyphicon glyphicon-shopping-cart"></span></span></a>';
                            } else {
                                    str += '<a href="' + window.contextRoot + '/cart/add/' + data + '/product" class="btn btn-success"><span class="glyphicon glyphicon-shopping-cart"></span></span></a>';                                     
                            }
                         }
                        return str;
                    }
                }
            ]
        });
    }


    /* for fading out the alert message after 3 seconds */
    $alert = $('.alert');
    if ($alert.length) {
        setTimeout(function () {
            $alert.fadeOut('slow');
        }, 3000)
    }

    // --------------------------


    // --------------------
    // DATA TABLE FOR ADMIN
    // ---------------------

    var $adminProductTable = $('#adminProductsTable');

    // execute the below code only where we have this table
    if ($adminProductTable.length) {
        //console.log('Inside the table.');

        var jsonUrl = window.contextRoot + '/json/data/admin/all/products';



        $adminProductTable.DataTable({
            lengthMenu: [
                [10, 20, 40, -1],
                ['10 Records', '20 Records', '40 Records', 'ALL']
            ],
            pageLength: 10,


            ajax: {
                url: jsonUrl,
                dataSrc: ''
            },
            columns: [{
                    data: 'id'
                },

                {
                    data: 'code',
                    mRender: function (data, type, row) {
                        return '<img src="' + window.contextRoot + '/resources/images/' + data + '.jpg" class="adminDataTableImg"/>';
                    }
                },
                {
                    data: 'name'
                },
                {
                    data: 'brand'
                },

                {
                    data: 'quantity',
                    mRender: function (data, type, row) {
                        if (data < 1) {
                            return '<span style="color:red">Out of Stock</span>';
                        }
                        return data;
                    }
                },

                {
                    data: 'unitPrice',
                    mRender: function (data, type, row) {
                        return '&euro; ' + data
                    }
                },

                {
                    data: 'active',
                    bSortable: false,
                    mRender: function (data, type, row) {
                        var str = '';
                        if (data) {
                            str += '<label class="switch"> <input type="checkbox" value="' + row.id + '" checked="checked">  <div class="slider round"> </div></label>';

                        } else {
                            str += '<label class="switch"> <input type="checkbox" value="' + row.id + '"><div class="slider round"> </div></label>';
                        }

                        return str;
                    }
                },
                {
                    data: 'id',
                    bSortable: false,
                    mRender: function (data, type, row) {
                        var str = '';

                        str += '<a href="'+window.contextRoot+'/manage/' + data + '/product" class="btn btn-warning">';
                        str += '<span class="glyphicon glyphicon-pencil"></span></a>';
                        return str;
                    }
                }
            ],


					initComplete: function () {
						var api = this.api();
						api.$('.switch input[type="checkbox"]').on('change' , function() {							
							var dText = (this.checked)? 'You want to activate the Product?': 'You want to de-activate the Product?';
							var checked = this.checked;
							var checkbox = $(this);
							debugger;
						    bootbox.confirm({
						    	size: 'medium',
						    	title: 'Product Activation/Deactivation',
						    	message: dText,
						    	callback: function (confirmed) {
							        if (confirmed) {

                                        console.log('value');
                                        var activationUrl = window.contextRoot + '/manage/product/' + checkbox.prop('value') + '/activation';

                                        $.post(activationUrl, function(data) {
                                            bootbox.alert({
                                                size: 'medium',
                                                title: 'Information',
                                                message: data
                                            });					            	
							            });
							        }
							        else {							        	
							        	checkbox.prop('checked', !checked);
                            }
                        }
                    });
                });
            }

        });
    }

    // validation form for category
     var $categoryForm = $('#categoryForm');

     if($categoryForm.length){
         $categoryForm.validate({
            rules: {

                name: {
                    required: false,
                    minLength: 2
                },
                description: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: 'Please add the category name',
                    minLength: 'The category name should not be less than 2 characters'
                },
                description: {
                    required: '"Please add a description'
                }
            },
            errorElement: 'em',
            errorPlacement: function(error,element){
                // add the class of help-block
                error.addClass('help-block');
                // add the error element after the input element
                error.insertAfter(element);
            }
         });
     }

     // validation for login form
      var $loginForm = $('#categoryForm');

     if($loginForm.length){
         $loginForm.validate({
            rules: {

                username: {
                    required: false,
                    email: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: 'Please add the username',
                    email: 'Please enter valid email address'
                },
                password: {
                    required: '"Please enter the password'
                }
            },
            errorElement: 'em',
            errorPlacement: function(error,element){
                // add the class of help-block
                error.addClass('help-block');
                // add the error element after the input element
                error.insertAfter(element);
            }
         });
     }
});