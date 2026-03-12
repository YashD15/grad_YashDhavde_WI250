import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student-service';

@Component({
  selector: 'app-update-student',
  standalone: false,
  templateUrl: './update-student.html',
  styleUrls: ['./update-student.css']
})
export class UpdateStudent implements OnInit {

  student: Student = {
    regNo: '',
    rollNo: 0,
    name: '',
    standard: 0,
    school: '',
    gender: '',
    percentage: 0
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studentService: StudentService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const regNo = this.route.snapshot.paramMap.get('regNo')!;
    this.studentService.getStudentByRegNo(regNo).subscribe({
      next: data => {
        this.student = data;
        this.cd.detectChanges();
      },
      error: err => console.error(err)
    });
  }

  onSubmit(formValue: Student) {
    this.studentService.updateStudent(formValue).subscribe({
      next: () => {
        alert('Student updated successfully!');
        this.router.navigate(['/students/details']);
      },
      error: err => console.error(err)
    });
  }

  cancel() {
    this.router.navigate(['/students/details']);
  }
}