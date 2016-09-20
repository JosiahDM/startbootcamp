<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!DOCTYPE html>
<html>
<head>

<%@ include file="sitehead.jsp" %>
<title> &#60;BOOT/PREP&#62; HOME</title>
</head>
<body>


<%@ include file="sitenavbar.jsp" %>

<!-- Carousel
    ================================================== -->
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <!-- Indicators -->
      <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
      </ol>
      <div class="carousel-inner" role="listbox">
        <div class="item active">
          <img class="first-slide hidden-xs" src="img/bannerphoto1.jpg" alt="First slide">
            <img class="first-slide hidden-sm hidden-md hidden-lg" src="img/bannerphoto1xs.jpg" alt="First slide">
          <div class="container">
            <div class="carousel-caption ">
              <h1 style="font-size: 3em;">Helpful Resources</h1>
              <p>&#60;BOOT/PREP&#62; is your source for curated articles that will help prepare you for coding bootcamp!</p>
              <p><a class="btn btn-lg btn-primary" href="resourcelist.jsp" role="button">Start Learning!</a></p>
            </div>
          </div>
        </div>
        <div class="item">
          <img class="first-slide hidden-xs" src="img/bannerphoto2.jpg" alt="First slide">
            <img class="first-slide hidden-sm hidden-md hidden-lg" src="img/bannerphoto2xs.jpg" alt="First slide">
          <div class="container verticalmiddle">
            <div class="carousel-caption">
              <h1 style="font-size: 3em;">Keep track of your progress...</h1>
              <p>With a &#60;BOOT/PREP&#62; account, you can add notes, rate articles, and add your own custom resources.</p>
              <p><a class="btn btn-lg btn-primary" href="userprofile.jsp" role="button">Sign Up Today!</a></p>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control hidden-xs" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control hidden-xs" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div><!-- /.carousel -->

<a href="userprofile.jsp?id=${userId}">ACCOUNT</a>
<a href="resourcelist.do">List Resources</a>
<%@ include file="sitefooter.jsp" %>
</body>
</html>
