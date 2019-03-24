import { Component } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { MatTableDataSource, MatDialog } from "@angular/material";

import { QuestionsService } from "./question.service";
import { Question } from "./question.model";
import { Page } from "../page/page.model";
import { DiagramComponent } from "../diagram/diagram.component";

@Component({
  selector: "student",
  templateUrl: "./student.component.html",
  styleUrls: ["./student.component.css"]
})
export class StudentComponent {
  markedQuestions: Question[];
  unmarkedQuestions: Question[];
  id: number;

  displayedColumnsMarked: string[] = [
    "questionText",
    "userResponse",
    "correct"
  ];
  dataSourceMarked: MatTableDataSource<Question>;
  displayedColumnsUnmarked: string[] = ["questionText", "userResponse"];
  dataSourceUnmarked: MatTableDataSource<Question>;

  constructor(
    private diagramDialog: MatDialog,
    private router: Router,
    activatedRoute: ActivatedRoute,
    private questionsService: QuestionsService
  ) {
    this.id = activatedRoute.snapshot.params["id"];
    this.getMarkedQuestions(this.id);
    this.getUnmarkedQuestions(this.id);
  }

  getMarkedQuestions(id: number) {
    this.questionsService
      .getMarkedQuestions(id)
      .subscribe(
        (data: Page<Question>) =>
          (this.dataSourceMarked = new MatTableDataSource(data["content"])),
        error => console.log(error)
      );
  }

  getUnmarkedQuestions(id: number) {
    this.questionsService
      .getUnmarkedQuestions(id)
      .subscribe(
        (data: Page<Question>) =>
          (this.dataSourceUnmarked = new MatTableDataSource(data["content"])),
        error => console.log(error)
      );
  }

  showDiagram() {
    this.diagramDialog.open(DiagramComponent, {
      height: "600px",
      width: "800px"
    });
  }
}
