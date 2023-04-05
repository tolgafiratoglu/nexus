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

});