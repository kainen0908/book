package com.bookmarket.book.controller;

import com.bookmarket.book.dto.BookDTO;
import com.bookmarket.book.dto.PageRequestDTO;
import com.bookmarket.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
@Log4j2
@RequiredArgsConstructor
public class BookStoreController {

    private final BookService bookService;

    @GetMapping({"/", "/main"})
    public String main(Model model) {
        log.info("Load main page...");

        model.addAttribute("result", bookService.getMainPageNewBook());
        return "main";
    }

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("login get.........");
        log.info("logout: " + logout);

        if(logout != null) {
            log.info("user logout");
        }
    }


    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/register")
    public void register() {

    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/register")
    public String register(BookDTO bookDTO, RedirectAttributes redirectAttributes) {
        log.info("bookDTO: " + bookDTO);
        Long bno = bookService.register(bookDTO);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", bookService.getListWithCategory(pageRequestDTO));
    }

    @GetMapping("/read")
    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("bno: " +bno);
        BookDTO bookDTO = bookService.getBook(bno);
        model.addAttribute("dto", bookDTO);
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/modify")
    public void modify(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("bno: " +bno);
        BookDTO bookDTO = bookService.getBook(bno);
        model.addAttribute("dto", bookDTO);
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes) {
        log.info("bno: " + bno);

        bookService.remove(bno);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/list";
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/modify")
    public String modify(BookDTO bookDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("Post Modify...");
        log.info("bookDTO: " + bookDTO);

        bookService.modify(bookDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("bno", bookDTO.getBno());

        return "redirect:/read";
    }
}
