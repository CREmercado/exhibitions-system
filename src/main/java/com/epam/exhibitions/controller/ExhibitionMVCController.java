package com.epam.exhibitions.controller;

import com.epam.exhibitions.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ExhibitionMVCController {

    private final ExhibitionService exhibitionService;

    /**
     * <p>Method setExhibitionState</p>
     * @param id the exhibition id to be updated
     * @return the redirection to the right page (stay in administratior view)
     * @since 1.0
     */
    @PostMapping("/administrator/set-exhibition-state")
    String setExhibitionState(@RequestParam("id") Long id) throws Exception {
        exhibitionService.setExhibitionState(id);
        return "redirect:/administrator";
    }

    @GetMapping("/administrator/add-exhibition")
    String addExhibition() {
        return "add-exhibition";
    }

}
