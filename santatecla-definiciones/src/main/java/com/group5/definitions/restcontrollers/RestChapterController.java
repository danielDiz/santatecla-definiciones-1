package com.group5.definitions.restcontrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.group5.definitions.model.Chapter;
import com.group5.definitions.model.Concept;
import com.group5.definitions.model.Justification;
import com.group5.definitions.services.ChapterService;
import com.group5.definitions.services.ConceptService;
import com.group5.definitions.services.JustificationService;
import com.group5.definitions.usersession.UserSessionService;

@RestController
@RequestMapping("/api")
public class RestChapterController {
	
	@Autowired
	private ChapterService chapterService;
	
	@Autowired
	private ConceptService conceptService;
	
	@Autowired
	private JustificationService justificationService;
	
	@Autowired
	private UserSessionService userSessionService;
	
	private final int DEFAULT_SIZE = 10;
	
	@JsonView(Chapter.Basic.class)
	@GetMapping(value = {"", "/chapters"})
	public Page<Chapter> getChapters(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
		Page<Chapter> chapters = chapterService.findAll(page);
		return chapters;
	}
	
	interface ChapterConcept extends Chapter.Basic, Chapter.Concepts, Concept.Basic {}
	@JsonView(ChapterConcept.class)
	@GetMapping("/chapters/{id}")
	public ResponseEntity<Chapter> getChaptersById(@PathVariable long id) {
		Chapter chapter = chapterService.findById(id);
		if (chapter!=null)
			return new ResponseEntity<>(chapter, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@JsonView(ChapterConcept.class)
	@RequestMapping(value="" , method = RequestMethod.POST)
	public ResponseEntity<Chapter> addChapter(@PathVariable String chapterName){
		Chapter chapter = new Chapter(chapterName);
		chapterService.save(chapter);
		return new ResponseEntity<>(chapter,HttpStatus.OK);
	}
	
	@JsonView(ChapterConcept.class)
	@RequestMapping(value="/chapters/{id}/deleteChapter", method = RequestMethod.DELETE)
	public ResponseEntity<Chapter> deleteChapter(@PathVariable Long id){
		Chapter chapter = chapterService.findById(id);
		chapterService.deleteById(id);
		if(chapter != null) {
			return new ResponseEntity<>(chapter,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(Concept.Basic.class)
	@RequestMapping(value="/concept/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Concept> deleteConcept(@PathVariable Long id){
		Concept concept = conceptService.findById(id);
		conceptService.deleteById(id);
		if(concept != null) {
			return new ResponseEntity<>(concept,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(Concept.Basic.class)
	@RequestMapping(value="/chapters/{id}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Concept addConcept(@RequestBody Concept concept, @PathVariable long id){
		Chapter chapter = chapterService.findById(id);
		chapter.getConcepts().add(concept);
		concept.setChapter(chapter);
		conceptService.save(concept);
		chapterService.save(chapter);
			return concept;
	}
	
	@JsonView(Concept.Basic.class)
	@RequestMapping(value="/justification/{justificationId}")
	public ResponseEntity<Justification> addJustification(@PathVariable Long justificationId,
			@PathVariable String conceptId, @PathVariable String justificationText, @PathVariable Boolean marked ){
		Concept concept=conceptService.findById(Long.parseLong(conceptId));
		Justification justification=new Justification(justificationText, marked, userSessionService.getLoggedUser());
		justificationService.save(justification);
		conceptService.save(concept);
		return new ResponseEntity<>(justification,HttpStatus.OK);
	}
	
	
	
}
