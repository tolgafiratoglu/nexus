$( document ).ready(function($) {

    $("#new_bucket_button").click(
        function (event) {
            event.preventDefault();
            
            var bucketName = $("#bucket_name").val();

            clickedButton = $(this);
            clickedButton.addClass("disabled");

            if(bucketName === "") {
                $(".alert-danger").html("Bucket name shouldn't be blank").show().delay(2000).fadeOut('slow');
                clickedButton.removeClass("disabled");
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

    $("#new_table_button").click(
        function(event){
            event.preventDefault();
            
            var tableName = $("#table_name").val();

            clickedButton = $(this);
            clickedButton.addClass("disabled");

            if(tableName === "") {
                $(".alert-danger").html("Table name shouldn't be blank").show().delay(2000).fadeOut('slow');
                clickedButton.removeClass("disabled");
            } else {
                var jqxhr = $.ajax( {
                    "method": "PUT", 
                    "url": "/dynamo/table/create",
                    "data": JSON.stringify({"name": tableName}),
                    "contentType": "application/json"
                } )
                    .done(function() {
                        document.location = "/dynamo/list";
                    })
                    .fail(function(xhr, status, error) {
                        $(".alert-danger").html(xhr.responseText).show().delay(2000).fadeOut('slow');
                        clickedButton.removeClass("disabled");
                    });

            }

        }
    );

    $("#insert_key_value").click(
        function(event) {
            event.preventDefault();

            var tableName = $("#table_name").find(":selected").val();
            var id = $("#item_id").val();
            var itemKey = $("#item_key").val();
            var itemValue = $("#item_value").val();
    
            $.ajax({
                "dataType" : 'json',
                "url" : "/dynamo/table/insert",
                "data" : JSON.stringify({"table": tableName, "id": id, "key": itemKey, "value": itemValue}),
                "type" : "PUT",
                "contentType": "application/json"
            }).done(function() {
                document.location = "/dynamo/table/insert";
            })
            .fail(function(xhr, status, error) {
                $(".alert-danger").html(xhr.responseText).show().delay(2000).fadeOut('slow');
                clickedButton.removeClass("disabled");
            });
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

        $("#save_report").click(
            function(event){
                event.preventDefault();

                var data = {};
                data["reportType"] = $('input[type=radio][name=report_option]:checked').val();
                data["title"] = $("#report_title").val();
                data["buckets"] = $("#bucket_list").val();
                data["tables"]  = $("#table_list").val();
                data["services"]  = $("#services").val();
                data["usageMetric"] = $("#usage_metric").find(":selected").val();
                data["s3Metric"] = $("#s3_metric").find(":selected").val();
                data["dynamoMetric"] = $("#dynamo_metric").find(":selected").val();

                $.ajax({
                    "url" : "/report/save",
                    "data" : JSON.stringify(data),
                    "type" : "PUT",
                    "contentType": "application/json"
                }).done(function() {
                    document.location = "/report/list";
                })
                .fail(function(xhr, status, error) {
                    $(".alert-danger").html(xhr.responseText).show();
                    clickedButton.removeClass("disabled");
                });
            }
        );

        $('input[type=radio][name=report_option]').change(function() {
            $(".form-element").addClass("collapse")
            $("." + this.value + "-related").removeClass("collapse"); 
        });

});