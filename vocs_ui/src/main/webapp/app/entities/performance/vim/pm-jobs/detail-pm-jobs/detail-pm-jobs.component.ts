import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IPmJobs } from 'app/shared/model/pmjobs.model';
import { MessageService } from 'primeng/api';
import { PmJobsService } from 'app/entities/performance/vim/pm-jobs/pm-jobs.service';
import { Dialog } from 'primeng/dialog';

@Component({
  selector: 'jhi-detail-pm-jobs',
  templateUrl: './detail-pm-jobs.component.html',
  styleUrls: ['./detail-pm-jobs.component.scss']
})
export class DetailPmJobsComponent implements OnInit {
  @ViewChild(Dialog, { static: true }) dialog;

  pmJobsDetail: IPmJobs;
  reportDetail: any;
  isDisplayReport = false;

  constructor(private activatedRoute: ActivatedRoute, private pmJobService: PmJobsService, private messageService: MessageService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pmJobsDetail }) => {
      this.pmJobsDetail = pmJobsDetail;
    });
  }

  showDialog(reportId: string) {
    this.reportDetail = null;
    this.isDisplayReport = true;
    this.pmJobService.findReportById(this.pmJobsDetail.id, reportId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.reportDetail = res.body.data;
          window.setTimeout(() => {
            this.dialog.center();
          });
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Pm Job report',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Pm Job report',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }
}
