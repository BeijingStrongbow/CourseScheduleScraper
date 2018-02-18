"use strict";

$("#course_name_field").hover(function(){
  if(document.getElementById("course_name_field").value === "Course name"){
    document.getElementById("course_name_field").value = "";
  }
}, function(){
  if(document.getElementById("course_name_field").value === ""){
    document.getElementById("course_name_field").value = "Course name"
  }
});

$("#course_number_field").hover(function(){
  if(document.getElementById("course_number_field").value === "Course number"){
    document.getElementById("course_number_field").value = "";
  }
}, function(){
  if(document.getElementById("course_number_field").value === ""){
    document.getElementById("course_number_field").value = "Course number"
  }
});

document.onkeypress = function(e){
  e = e || window.event;

  if(e.keyCode == 13){
    let request = new XMLHttpRequest();
    let course_num = document.getElementById("course_number_field").value;
    let course_name = document.getElementById("course_name_field").value;
    course_num.replace(" ", "%20");
    course_name.replace(" ", "%20");

    request.onreadystatechange = function() {
      if(request.readyState == 4 && request.status == 200){
        console.log(request.responseText);
      }
      else if(request.status == 400){
        console.log("Invalid request");
      }
    }
    request.open("GET", "http://localhost/coursescheduler/search/course_number=" + course_num + "&course_name=" + course_name);
    request.send();
  }
}
