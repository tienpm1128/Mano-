import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IPmJobs } from 'app/shared/model/pmjobs.model';
import { PmJobsService } from 'app/entities/performance/ns/pm-jobs/pm-jobs.service';
import { MessageService } from 'primeng/api';
import { Dialog } from 'primeng/dialog';

@Component({
  selector: 'jhi-detail-pm-jobs',
  templateUrl: './detail-pm-jobs.component.html',
  styleUrls: ['./detail-pm-jobs.component.scss']
})
export class DetailPmJobsComponent implements OnInit {
  @ViewChild(Dialog, { static: true }) dialog;
  @ViewChild('dt', { static: true }) dt;

  pmJobsDetail: IPmJobs;
  reportDetail: any;
  isDisplayReport = false;

  first = 0;
  lastRowsPerPage = 10;

  constructor(
    private activatedRoute: ActivatedRoute,
    private pmJobService: PmJobsService,
    private messageService: MessageService,
    private cdr: ChangeDetectorRef
  ) {}

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

  onSortOrPage() {
    if (this.dt.filteredValue.length > 0) {
      if (this.dt._rows !== this.lastRowsPerPage) {
        this.resetTable();
        this.lastRowsPerPage = this.dt._rows;
      }
    }
  }

  resetTable() {
    this.first = -1;
    window.setTimeout(() => {
      this.first = 0;
      this.cdr.detectChanges();
    });
  }
}
