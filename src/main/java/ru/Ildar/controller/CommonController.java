package ru.Ildar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.Ildar.entity.RecordStatus;
import ru.Ildar.entity.dto.RecordsContainerDto;
import ru.Ildar.service.RecordService;



@Controller
public class CommonController {
    private final RecordService recordService;

    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
    public String redirectHomePage() {
        return "redirect:/home";
    }


    @RequestMapping("/home")
    public String getMainPage(Model model, @RequestParam(name = "filter", required = false ) String filterMode) {
       RecordsContainerDto contaainer =  recordService.findAllRecords(filterMode);

       model.addAttribute("records", contaainer.getRecords());
       model.addAttribute("numberOfDoneRecords", contaainer.getNumberOfDoneRecords());
       model.addAttribute("numberOfActiveRecords", contaainer.getNumberOfActiveRecords() );
        return "main-page";
    }

    @RequestMapping(value = "/add-record",method = RequestMethod.POST)
    public String addRecord(@RequestParam() String title) {
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/make-record-done",method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam() int id,
                                 @RequestParam(name="filter", required = true) String filterMode) {
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");

    }

    @RequestMapping(value = "/delete-record",method = RequestMethod.POST)
    public String deleteRecord(@RequestParam() int id,
                               @RequestParam(name="filter", required = true) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }
}

