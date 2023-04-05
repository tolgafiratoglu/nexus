$( document ).ready(function($) {

    $("#new_bucket_button").click(
        function (event) {
            event.preventDefault();
            
            var bucketName = $("#bucket_name").val();

            clickedButton = $(this);
            clickedButton.addClass("disabled");

            if(bucketName === "") {
                $(".alert-danger").html("Bucket name shouldn't be blank").show().delay(2000).fadeOut('slow');
            } else {
                var jqxhr = $.ajax( {
                    "method": "PUT", 
                    "url": "/bucket/create",
                    "data": JSON.stringify({"name": bucketName}),
                    "contentType": "application/json"
                } )
                    .done(function() {
                        document.location = "/bucket/list";
                    })
                    .fail(function(xhr, status, error) {
                        $(".alert-danger").html(xhr.responseText).show().delay(2000).fadeOut('slow');
                        clickedButton.removeClass("disabled");
                    });

            }        
        }
    );

    var files = [];

    $(document)
        .on(
            "change",
            "#file_loader",
            function(event) {
                files=event.target.files;
            });

    $("#new_bucket_object").click(
        function (event) {
            event.preventDefault();

            var bucketName = $("#bucket_name").find(":selected").val();

            clickedButton = $(this);
            clickedButton.addClass("disabled");

            var uploadForm = new FormData();
            uploadForm.append("file", files[0]);

            $.ajax({
                    dataType : 'json',
                    url : "/bucket/upload/" + bucketName,
                    data : uploadForm,
                    type : "PUT",
                    enctype: 'multipart/form-data',
                    processData: false, 
                    contentType:false,
                    success : function(result) {
                        //...;
                    },
                    error : function(result){
                        //...;
                    }
                });

        }    
    );

});