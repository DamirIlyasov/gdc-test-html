$(document).ready(function () {

    $("#build_html_btn").click(function () {
        $(".built_html").empty();
        $.ajax('/build_html', {
            type: 'GET',
            data: {
                text: $("#input_text").val()
            },
            success: function (t) {
                $("#input_text").text("");
                if (t !== "") {
                    $(".built_html").append(t);
                } else {
                    $(".built_html").empty();
                }
            }
        });
    })
});
