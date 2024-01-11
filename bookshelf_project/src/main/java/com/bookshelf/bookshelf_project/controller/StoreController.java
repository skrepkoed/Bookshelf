package  com.bookshelf.bookshelf_project.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshelf.bookshelf_project.entity.Store;
import com.bookshelf.bookshelf_project.repository.ItemRepository;
import com.bookshelf.bookshelf_project.repository.StoreRepository;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping(path = "admin/", method=RequestMethod.GET)
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/stores")
    public ModelAndView getAllStores(){
        ModelAndView mav= new ModelAndView("list-stores");
        mav.addObject("stores",  storeRepository.findAll());
        return mav;
    }

    @GetMapping("/addStoreForm")
    public ModelAndView addStoreForm(){
        ModelAndView mav = new ModelAndView("add-store-form");
        Store store = new Store();
        mav.addObject("store", store);
        return mav;
    }

    @PostMapping("/saveStore")
    public String saveStore(@ModelAttribute Store store){
        storeRepository.save(store);
        return "redirect:/admin/stores";
    }

    @GetMapping("/showUpdateStoreForm")
    public ModelAndView showUpdateForm(@RequestParam Long storeId){
        ModelAndView mav = new ModelAndView("add-store-form");
        Optional<Store> optionalStore= storeRepository.findById(storeId);
        Store store = new Store();
        if (optionalStore.isPresent()) {
            store = optionalStore.get();
        }
        mav.addObject("store", store);
        return mav;
    }
    @Transactional
    @GetMapping("/deleteStore")
    public String deleteStore(@RequestParam Long storeId){
        itemRepository.deleteByStore(storeRepository.getReferenceById(storeId));
        storeRepository.deleteById(storeId);
        return "redirect:/admin/stores";
    }
}