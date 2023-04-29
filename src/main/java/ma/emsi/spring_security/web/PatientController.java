package ma.emsi.spring_security.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.emsi.spring_security.entities.Patient;
import ma.emsi.spring_security.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String Home(){
        return "redirect:/patients";
    }

    @GetMapping(path = "/patients")
    public String AfficherPatients(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listePatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String Delete(Long id, int page, String keyword){
        patientRepository.deleteById(id);
        return "redirect:/patients?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model ){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }

    @PostMapping(path = "/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(@Valid Patient patient, BindingResult bindingResult, Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if (bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/patients?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(Long id, int page, String keyword,Model model){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient == null) throw new RuntimeException("Patient Inexistant");
        model.addAttribute("patient",patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editPatient";
    }

}
