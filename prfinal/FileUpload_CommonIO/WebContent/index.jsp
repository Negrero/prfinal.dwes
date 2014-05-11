<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<title>Insert title here</title>
<script>
	$(document).ready(function() {
		//add more file components if Add is clicked
		$('#addFile').click(function() {
			var fileIndex = $('#fileTable tr').children().length - 1;
			var data = '<tr><td>' + '<input type="file" name="files['+ fileIndex +']" />' + '</td></tr>';
			$('#fileTable').append(data);
		});
	});
</script>
</head>
<body>
	<center>
		<input id="addFile" type="button" value="Add File" /> <br />
		<form  method="POST" action="DataUploadServlet"
			enctype="multipart/form-data">
			<table id="fileTable">
				<tr>
					<td><input name="files[0]" type="file" /></td>
				</tr>
			</table>
			<input type="submit" value="Upload" />
		</form>
	</center>
</body>
</html>