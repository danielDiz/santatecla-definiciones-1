import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { StudentComponent } from "./student.component";
import { TeacherComponent } from './teacher/teacher.component';

const routes: Routes = [{ path: "concept/:id", component: StudentComponent},
                        { path: "teacher/:id", component: TeacherComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
