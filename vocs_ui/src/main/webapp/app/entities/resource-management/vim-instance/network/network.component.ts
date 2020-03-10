import { Component, OnInit } from '@angular/core';
import { INetWork } from 'app/shared/model/network.model';
import { NetworkService } from './network.service';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core';

@Component({
  selector: 'jhi-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.scss']
})
export class NetworkComponent implements OnInit {
  netWorks: INetWork[];
  isCheckAll = false;
  vimId: string;
  isDisplayCreate = false;
  isAdmin = false;

  constructor(private netWorkService: NetworkService, private activatedRoute: ActivatedRoute, private accountService: AccountService) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
  }

  ngOnInit() {
    this.loadNetWork();
  }

  loadNetWork() {
    this.isCheckAll = false;
    this.netWorkService.query(this.vimId).subscribe(res => {
      this.netWorks = res.body.data;
    });
  }
}
