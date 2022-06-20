package tn.esprit.spring.services;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	// Add logs to this class:
	Logger log = LoggerFactory.getLogger(EntrepriseServiceImpl.class);
	@Autowired
	EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;

	public int ajouterEntreprise(Entreprise entreprise) {
		try {
			log.info("Start the method of adding the company");
			log.debug("Start the method of adding the company");
			entrepriseRepoistory.save(entreprise);
			log.info("Finishing the method of adding the company with success");
			log.debug("Finishing the method of adding the company with success");
		}catch(Exception e){
			log.error("The method of adding the company fails "+e);
		}
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		try{
			log.info("Start the method of adding the department");
			log.debug("Start the method of adding the department");
			deptRepoistory.save(dep);
			log.info("Finishing the method of adding the department with success");
			log.debug("Finishing the method of adding the department with success");
		}catch(Exception e){
			log.error("The method of adding the department fails "+e);
		}

		return dep.getId();
	}

	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement
		//donc il faut rajouter l'entreprise a departement
		// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
		//Rappel : la classe qui contient mappedBy represente le bout Slave
		//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		log.info("Start the method of adding the department to the company");
		log.debug("Start the method of adding the department to the company");
		try{
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
			Departement depManagedEntity = deptRepoistory.findById(depId).orElse(null);
			if (depManagedEntity!=null){
				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity);
				log.info("Finishing the method of adding the department to the company with success");
				log.debug("Finishing the method of adding the department to the company with success");

			}
		}catch(Exception e){
			log.error("The method of adding the department to the company fails "+e);
		}


	}



	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		log.info("Start the method of deleting the company");
		log.debug("Start the method of deleting the company");
		try {

			Optional<Entreprise> entreprise = entrepriseRepoistory.findById(entrepriseId);
			if(entreprise.isPresent()){
				Entreprise entrp = entreprise.get();
				entrepriseRepoistory.delete(entrp);
				log.info("Finishing the method of deleting the company with success");
				log.debug("Finishing the method of deleting the company with success");

			}

		} catch (Exception e) {
			log.error("The method of deleting the company fails "+e);
		}

	}

	@Transactional
	public void deleteDepartementById(int depId) {

		log.info("Start the method of deleting the department");
		log.debug("Start the method of deleting the department");
		try {
			Optional<Departement> departement = deptRepoistory.findById(depId);
			if(departement.isPresent()){
				Departement dp = departement.get();
				deptRepoistory.delete(dp);
				log.info("Finishing the method of deleting the department with success");
				log.debug("Finishing the method of deleting the department with success");

			}
		} catch (Exception e) {
			log.error("The method of deleting the department fails "+e);
		}
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		log.info("Start the method of getting the Company");
		log.debug("Start the method of getting the Company");
		try {
			Entreprise et= entrepriseRepoistory.findById(entrepriseId).orElse(null);
			log.debug("Finishing the method of getEntrepriseById with success ");
			return et;
		} catch (Exception e) {
			log.error("The method of getEntrepriseById fails: " +e);
			return null;
		}
	}

}
