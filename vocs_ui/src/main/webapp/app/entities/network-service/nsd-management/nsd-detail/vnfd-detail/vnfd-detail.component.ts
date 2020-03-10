import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IVnfd } from 'app/shared/model/vnfd.model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'jhi-vnfd-detail',
  templateUrl: './vnfd-detail.component.html',
  styleUrls: ['./vnfd-detail.component.scss']
})
export class VnfdDetailComponent implements OnInit {
  vnfdDetail: IVnfd;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vnfdDetail }) => {
      this.vnfdDetail = vnfdDetail;
    });
  }

  objToString(obj: object) {
    if (obj) {
      return Object.entries(obj);
    }
    return [];
  }
}
