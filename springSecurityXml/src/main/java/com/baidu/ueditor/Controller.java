package com.baidu.ueditor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*String rootPath = application.getRealPath( "/" );
		out.write( new ActionEnter( request, rootPath ).exec() );*/
		
		String realPath = req.getServletContext().getRealPath("/");
		resp.getWriter().write(new ActionEnter(req, realPath).exec());
	}
	
}
