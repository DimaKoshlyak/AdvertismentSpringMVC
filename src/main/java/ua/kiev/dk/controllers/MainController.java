package ua.kiev.dk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.dk.dao.AdvDAO;
import ua.kiev.dk.entities.Advertisement;
import ua.kiev.dk.entities.AdvertisementList;
import ua.kiev.dk.entities.Photo;
import ua.kiev.dk.services.AdvManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Controller
@RequestMapping("/SpringMVC")
public class MainController {

	@Autowired
	private AdvDAO advDAO;

	@Autowired
	private AdvManager advManager;

	@RequestMapping("/")
	public ModelAndView listAdvs() {
		return new ModelAndView("index", "advs", advManager.list());
	}

	@RequestMapping(value = "/add_page", method = RequestMethod.POST)
	public String addPage(Model model) {
		return "add_page";
	}

	@RequestMapping(value = "/trash_page", method = RequestMethod.POST)
	public ModelAndView trashPage() { return new ModelAndView("trash","advs",advDAO.listInTray()); }

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
		return new ModelAndView("index", "advs", advDAO.list(pattern));
	}

//	@RequestMapping("/move_to_trash")
//	public ModelAndView moveToTrash(@RequestParam(value="id") long id) {
//		advDAO.moveToTrash(id);
//		return new ModelAndView("index", "advs", advDAO.list());
//	}

	@RequestMapping("/move_to_trash")
	public ModelAndView moveToTrash(@RequestParam(value = "id") long id){
		Advertisement adv = advDAO.getAdv(id);
		adv.setTo_del(true);
		System.out.println("Start merging");
		System.out.println("is to del = " + adv.isTo_del());
		return new ModelAndView("index", "advs", advDAO.list());
	}

	@RequestMapping(value = "/process_checked", method = RequestMethod.POST)
	public ModelAndView processChecked(HttpServletRequest request) {
		String[] str = request.getParameterValues("selectrow[]");
		String whatToDo = request.getParameter("submit");
		if (str != null)
			for (String idstr : str) {
				if (whatToDo.equals("delete")) {
					advDAO.delete(Long.valueOf(idstr));
				} else if (whatToDo.equals("restore")) {
					advDAO.restoreFromTrash(Long.valueOf(idstr));
				}
			}
		return new ModelAndView("trash", "advs", advDAO.listInTray());
	}

	@RequestMapping("/image/{file_id}")
	public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("file_id") long fileId) {
		try {
			byte[] content = advDAO.getPhoto(fileId);
			response.setContentType("image/png");
			response.getOutputStream().write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addAdv(@RequestParam(value="name") String name,
						 @RequestParam(value="shortDesc") String shortDesc,
						 @RequestParam(value="longDesc", required=false) String longDesc,
						 @RequestParam(value="phone") String phone,
						 @RequestParam(value="price") double price,
						 @RequestParam(value="photo") MultipartFile photo,
						 HttpServletRequest request,
						 HttpServletResponse response)
	{
		try {
			Advertisement adv = new Advertisement(
					name, shortDesc, longDesc, phone, price,
					photo.isEmpty() ? null : new Photo(photo.getOriginalFilename(), photo.getBytes())
			);
			advDAO.add(adv);
			return new ModelAndView("index", "advs", advDAO.list());
		} catch (IOException ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
	}

	@RequestMapping("/load_xml")
	public ModelAndView importXml(@RequestParam(value = "xmlfile") MultipartFile mfile) {
		InputStream inputStream = null;
		Reader reader = null;
		try {
			inputStream = mfile.getInputStream();
			reader = new InputStreamReader(inputStream, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		JAXBContext jaxbContext = null;
		Unmarshaller unmarshaller = null;
		AdvertisementList advList = null;
		try {
			jaxbContext = JAXBContext.newInstance(Advertisement.class);
			unmarshaller = jaxbContext.createUnmarshaller();
			advList = (AdvertisementList) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		for (Advertisement adv : advList.getAdvList()) {
			advDAO.add(adv);
		}
		return new ModelAndView("info", "advs", advDAO.list());
	}
}