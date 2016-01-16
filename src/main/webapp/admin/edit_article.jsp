<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zchen323.photo.data.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>Edit Article</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>
<%
	Article a = (Article)request.getAttribute("article");
	String articleActive = "checked";
	if(!a.isActive()){
		articleActive = "";
	}
%>
<form>
<input type="hidden" name="id" value="<%=a.getId() %>" />
Title:<br />
<input type="text" name="title" value="<%=a.getTitle() %>" <%=articleActive %> /><br />
<input type="checkbox" name="articleActive" <%=articleActive %>/><br />
Description:<br />
<textarea name="content" rows="5" cols="50"><%=a.getContent() %></textarea><br />
<br />
<div id="images">
<%
	List<Photo> list = a.getPhotos();
	int count = 1000;
	for(Iterator<Photo> it = list.iterator(); it.hasNext();){
		Photo p = it.next();
		String checked = "";
		if(p.isActive()){
			checked = "checked";
		}
%>

<br />Image title:<br />
<input type="text" name="imagetitle_<%=count %>" value="<%=p.getTitle() %>" size="70"/><br />
Image url:<br />
<input type="text" name="imageurl_<%=count %>" value="<%=p.getUrl() %>" size="70" /><br />
or image location from local:<br />
<input type="file" name='<%=count %>' class='addfile'/><br />
Image description:<br />
<textarea name="imagedesc_<%=count %>"rows="2" cols="50"><%=p.getDescription() %></textarea><br />	
Active: <input name="imageactive_<%=count %>" type="checkbox" <%=checked %>/>
<%	
		count++;
	}
%>
</div>

<input type="button"  id="addimage" value="Add Image"></input>
<input type="button" id="submit" value="Submit"></input>


</form>

<script>

$(document).ready(function(){	
	var originalCount = <%=count %>
	var count = originalCount;
	$("#addimage").click(function(){
		var title = "<br />Title:<br /><input type='text' name='imagetitle_" + count + "' size='70'><br />";
		var url = "Image URL:<br /><input type='text' name='imageurl_" + count + "' size='70'><br />";
		var imageFile = "Or image file:<br /><input class='addfile' type='file' name='" + count + "'><br />";
		var desc = "Description:<br /><textarea name='imagedesc_" + count + "' rows='2' cols='50'></textarea> <br />";					   
		var active = "Active:<input type='checkbox' name='imageactive_" + count + "' checked><br />";
		$("#images").append(title + url + imageFile+ desc + active);
		$('.addfile').on('change', function(event){
			var str2 = 'input[name="imageurl_' + this.name + '"]';
			var text2 = $('#images').find(str2).val();
			var myelement = $('#images').find(str2);
			myelement.val("wait...");
			var imagedata = loadimage(event, myelement);
		});			
		count++;
	  });

	$("#submit").click(function(){
			var data = $("form").serialize();
			data = data + "&count="+count;
			var formData = $("form").serialize();		
			formData = formData + "&action=update&count="+count;
			console.log(formData);
			 $.ajax({
					url: "_admin",
					type: "POST",
					data: formData,
					success: function(data, textStatus, jqXHR){
						alert("Success");
						$("#images").empty();
						window.location = 'photo?id='+data;
					},
					error: function(jqXHR, textStatus, errorThrown){
						alert("Fail");
					}
				 });
			count = originalCount;
			
	 });

	$('.addfile').on('change', function(event){
		var str2 = 'input[name="imageurl_' + this.name + '"]';
		var text2 = $('#images').find(str2).val();
		var myelement = $('#images').find(str2);
		myelement.val("wait...");
		var imagedata = loadimage(event, myelement);
	});	
	  
});
function loadimage(event, myelement){
	   var input = event.target;
	    var reader = new FileReader();
	    var imagedata = "";
	    reader.onload = function(){
	      var dataURL = reader.result;
	      imagedata = dataURL;
	      resize(imagedata, myelement);
	    };
	    reader.readAsDataURL(input.files[0]);
}

function resize(imagedata, myelement){
	var img = new Image();
	img.src = imagedata;
	console.log(img);
	
	img.onload = function(){
		var MAX_HEIGHT = 400;
		var MAX_WIDTH = 600;
		var imageHeight = img.height;
		var imageWidth = img.width;
		var ratial = 1;
		if(img.width > MAX_WIDTH){
			ratial = MAX_WIDTH/img.width;
		}else if(img.height > MAX_HEIGHT){
			ratial = MAX_HEIGHT/img.height;
		}
		imageWidth = img.width * ratial;
		imageHeight = img.height*ratial;

		//alert(imageWidth + ", " + imageHeight + ", " + ratial);

		var canvas = document.createElement("canvas");
		canvas.height = imageHeight;
		canvas.width = imageWidth;

		var ctx = canvas.getContext("2d");	
		ctx.scale(ratial, ratial);

		ctx.drawImage(img, 0, 0);
		//var output = document.getElementById('originalImage');		
    	
    	var newdata = canvas.toDataURL("image/jpeg", 0.7);
    	//output.src = newdata;
    	myelement.val(newdata);
	}
}

</script>

</body>
</html>