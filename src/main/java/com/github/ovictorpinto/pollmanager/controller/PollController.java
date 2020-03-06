package com.github.ovictorpinto.pollmanager.controller;

import com.github.ovictorpinto.pollmanager.business.PollBusiness;
import com.github.ovictorpinto.pollmanager.business.ResponseBusiness;
import com.github.ovictorpinto.pollmanager.exception.BusinessException;
import com.github.ovictorpinto.pollmanager.helper.HashIdHelper;
import com.github.ovictorpinto.pollmanager.helper.SessionHelper;
import com.github.ovictorpinto.pollmanager.model.ChartItem;
import com.github.ovictorpinto.pollmanager.model.Poll;
import com.github.ovictorpinto.pollmanager.model.PollItem;
import com.github.ovictorpinto.pollmanager.model.Response;
import com.github.ovictorpinto.pollmanager.repository.PollRepository;
import com.github.ovictorpinto.pollmanager.repository.ResponseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes("sessionHelper")
public class PollController {

    private final PollRepository pollRepository;
    private final HashIdHelper hashIdHelper;
    private final ResponseBusiness responseBusiness;
    private final ResponseRepository responseRepository;
    private final SessionHelper sessionHelper;
    private final PollBusiness pollBusiness;

    public PollController(PollRepository pollRepository, HashIdHelper hashIdHelper, ResponseBusiness responseBusiness, ResponseRepository responseRepository, SessionHelper sessionHelper, PollBusiness pollBusiness) {
        this.pollRepository = pollRepository;
        this.hashIdHelper = hashIdHelper;
        this.responseBusiness = responseBusiness;
        this.responseRepository = responseRepository;
        this.sessionHelper = sessionHelper;
        this.pollBusiness = pollBusiness;
    }

    @ModelAttribute("sessionHelper")
    public SessionHelper getSessionHelper() {
        return sessionHelper;
    }

    @GetMapping({"/", "poll/", "poll/list"})
    public String index(Model model) {
        List<Poll> polls = Collections.emptyList();
        if (sessionHelper.isLogged()) {
            polls = pollRepository.listBy(sessionHelper.getUserLogged());
        }
        model.addAttribute("pollList", polls);
        return "poll/list";
    }

    @GetMapping("poll/create")
    public String loadCreate(Model model) {
        model.addAttribute("poll", new Poll());
        return "poll/form";
    }

    @GetMapping("poll/edit/{id}")
    public String loadEdit(@PathVariable long id, Model model) {
        Poll poll = pollRepository.findBy(id);
        model.addAttribute("poll", poll);
        return "poll/form";
    }

    @Transactional
    @PostMapping("poll/create")
    public ModelAndView create(Poll poll, String... itens) {
        List<PollItem> pollItemList = new ArrayList<>();
        if (itens != null) {
            for (String item : itens) {
                pollItemList.add(new PollItem(pollItemList.size(), item));
            }
        }
        poll.setItemList(pollItemList);
        poll.setUser(sessionHelper.getUserLogged());
        pollBusiness.validatePoll(poll);
        pollRepository.save(poll);
        return new ModelAndView("redirect:/poll/list");
    }

    @GetMapping("poll/vote/{hashedId}")
    public String loadVote(@PathVariable String hashedId, Model model) {
        long id;
        try {
            id = hashIdHelper.decrypt(hashedId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid id");
        }
        Poll poll = pollRepository.findBy(id);
        model.addAttribute("poll", poll);
        return "poll/vote";
    }

    @Transactional
    @PostMapping("poll/vote")
    public String vote(String hashedId, Long idPollItem, Model model) {
        long idPoll;
        try {
            idPoll = hashIdHelper.decrypt(hashedId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid id");
        }
        try {
            if(idPollItem == null){
                throw new BusinessException("Choose an option");
            }
            responseBusiness.validateVote(idPoll, idPollItem);
            Response response = new Response();
            response.setPollItem(pollRepository.findItemBy(idPollItem));
            responseRepository.save(response);
            pollRepository.setResponded(idPoll);
            return "redirect:/poll/thanks";
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return loadVote(hashedId, model);
        }
    }

    @GetMapping("poll/thanks")
    public String thanks() {
        return "poll/thanks";
    }

    /**
     * Generate axys itens to populate de Chart
     * @param id
     * @param model
     * @return
     */
    @GetMapping("poll/response/{id}")
    public String loadResponse(@PathVariable long id, Model model) {
        Poll poll = pollRepository.findBy(id);
        model.addAttribute("poll", poll);

        List<ChartItem> responses = pollRepository.findResponseOf(poll);
        StringBuilder axisX = new StringBuilder();
        StringBuilder axisY = new StringBuilder();
        for (ChartItem item : responses) {
            axisX.append("'").append(item.getDescription()).append("',");
            axisY.append(item.getTotal()).append(",");
        }
        if (axisX.length() > 0) {//remove final comma
            axisX.delete(axisX.length() - 1, axisX.length());
            axisY.delete(axisY.length() - 1, axisY.length());
        }
        model.addAttribute("resultAxisX", axisX.toString());
        model.addAttribute("resultAxisY", axisY.toString());
        return "poll/response";
    }
}
