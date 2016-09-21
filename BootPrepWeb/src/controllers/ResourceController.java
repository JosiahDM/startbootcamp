package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import dao.BootPrepDAO;
import entities.Resource;
import entities.ResourceTag;
import entities.User;
import entities.UserData;
import entities.UserDataKey;

@Controller
@SessionAttributes({"userId","auth", "username"})
public class ResourceController {

	@Autowired
	private BootPrepDAO dao;
	
	// Session Attributes
	@ModelAttribute("userId")
	public int initUserId() {
		return 0;
	}
	@ModelAttribute("auth")
	public String initAuth() {
		return "";
	}
	@ModelAttribute("username")
	public String initUserName() {
		return "";
	}
	
	@RequestMapping(path="resourcelist.do")
	public ModelAndView listAllResources(@ModelAttribute("userId") int userId,
										 String view) {
		List<Resource> resources = new ArrayList<>();
		ModelAndView mv = new ModelAndView("resourcelist.jsp");
		if (view == null) view = "";
		switch (view) {
		case "add":
			resources = dao.getAllResourcesNotAdded(userId);
			mv.addObject("add", true);
			break;
		case "my":
			resources = dao.getAllResourcesById(userId);
			break;
		default:
			resources = dao.getAllResources();
			break;
		}
		mv.addObject("resources", resources); 
		return mv;
	}
	
	@RequestMapping(path="resourceadd.do")
	public ModelAndView addResourceToUser(@ModelAttribute("userId") int userId,
										  @ModelAttribute("auth") String auth,
										  int resourceId) {
		ModelAndView mv = new ModelAndView("userprofile.jsp");
		User u = dao.getUserById(userId);
		dao.addResourceToUser(userId, resourceId);
		List<Resource> resources = dao.getAllResourcesById(userId);
		mv.addObject("user", u);
		mv.addObject("resources", resources);
		return mv;
	}
	
	@RequestMapping(path="resource.do")
	public ModelAndView viewResource(@ModelAttribute("userId") int userId,
			@ModelAttribute("auth") String auth,
			int resourceId) {
		ModelAndView mv = new ModelAndView("resource.jsp");
		mv = viewLoader(mv, userId, resourceId);
		return mv;
	}
	

	
	@RequestMapping(path="resourceRemove.do")
	public ModelAndView removeResourceFromUser(@ModelAttribute("userId") int userId,
									   @ModelAttribute("auth") String auth,
									   @RequestParam(value="view", required=false) String view,
											   int resourceId) {
		ModelAndView mv = null;
		if (view.equals("list")) {
			listAllResources(userId, "my");
		} else if (view.equals("resource")) {
			mv.setViewName("resource.jsp");
			viewLoader(mv, userId, resourceId);
		} else {
			mv.setViewName("userprofile.jsp");
		}
		User u = dao.removeResourceFromUser(userId, resourceId);
		mv.addObject("user", u);
		mv.addObject("resources", u.getResources());
		return mv;
	}
	
	@RequestMapping(path="resourceCreate.do")
	public ModelAndView goToCreatePage(@ModelAttribute("userId") int userId,
			   @ModelAttribute("auth") String auth,
			   String url, String name, String description ) {
		ModelAndView mv = new ModelAndView("resourcelist.jsp");
		Resource r = dao.createResource(new Resource(url, name, description));
		mv.addObject("newResource", r);
		return mv;
	}
	
	@RequestMapping(path="resourceTagEdit.do")
	public ModelAndView addTagToResource(@ModelAttribute("userId") int userId,
			@ModelAttribute("auth") String auth,
			@RequestParam(value="action", required=false) String action, 
			@RequestParam(value="tagName", required=false) String tagName,
			@RequestParam(value="tagId", required=false) Integer tagId,
			int resourceId) {
		ModelAndView mv = new ModelAndView("resource.jsp");
		Resource r = null;
		if (userId == 0 || auth != "true") {
			mv.setViewName("userprofile.jsp");
			return mv;
		}
		switch (action) {
		case "add":
			addTag(mv, tagName, userId, resourceId);
			break;
		case "remove":
			removeTag(mv, userId, resourceId, tagId);
			break;
		default:
			viewLoader(mv, userId, resourceId);
			break;
		}
		return mv;
	}
	
	private void addTag(ModelAndView mv, String tagName, int userId, int resourceId) {
		Resource r = null;
		try { // exception thrown if resource already has the tag
			r = dao.addTagToResource(tagName, userId, resourceId);
		} catch (JpaSystemException cve) {
			r = dao.getResourceById(resourceId);
			mv.addObject("error", "Resource already has this tag.");
		}
		viewLoader(mv, userId, resourceId);
	}
	
	private void removeTag(ModelAndView mv, int userId, int resourceId, int tagId) {
		Resource r = null;
		r = dao.removeTagFromResource(userId, resourceId, tagId);
		if (r == null) {
			mv.addObject("error", "Can only remove tags that you created.");
			r = dao.getResourceById(resourceId);
		}
		viewLoader(mv, userId, resourceId);
	}
	
	// general load things into view via the needed IDs
	private ModelAndView viewLoader(ModelAndView mv, int userId, int resourceId) {
		UserDataKey key = new UserDataKey(userId, resourceId);
		UserData ur = dao.getUserDataByKey(key);
		List<Integer> rtags = dao.resourceTagUserIds(userId, resourceId);
		if (ur != null && ur.getUser().getId() == userId) {
			mv.addObject("userHasResource", true);
		}
		Resource r = dao.getResourceById(resourceId);
		mv.addObject("userData", ur);
		mv.addObject("resource", r);
		mv.addObject("rTags", rtags);
		return mv;
	}
	
	
}
