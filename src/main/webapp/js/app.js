$(document).ready(function () {
    $('#showForm').on("click", function () {
        console.log("ok");
        var form = $('#createShoppingList')
        if (form.css("display") === "none") {
            form.fadeIn("slow");
        } else {
            form.fadeOut("slow")
        }
    })
})
