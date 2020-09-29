<%
	try {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if ("admin".equals(username) && "1".equals(password)) {
			out.print("login succeed");
		} else {
			out.print("login failure");
		}
	} catch (Exception e) {
		out.print("login error");
	}
%>


