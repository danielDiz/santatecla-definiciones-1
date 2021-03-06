import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { StudentComponent } from "./student/student.component";
import { TeacherComponent } from './teacher/teacher.component';
import { ChapterComponent } from './home/main.component';

const routes: Routes = [{ path: "concept/:id", component: StudentComponent},
                        { path: "teacher/:id", component: TeacherComponent},
                        { path: "", component: ChapterComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
