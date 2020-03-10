import { Component, OnInit } from '@angular/core';
import { UserVimService } from 'app/entities/resource-management/vim-instance/user-vim/user-vim.service';
import { MessageService } from 'primeng/api';
import { IUserVim } from 'app/shared/model/userVim.model';
import { VIM_ROLES } from 'app/app.constants';
import { IRole } from 'app/shared/model/roles.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'manoPickList',
  templateUrl: './pick-list.component.html',
  styleUrls: ['./pick-list.component.scss']
})
export class PickListComponent implements OnInit {
  users: IUserVim[];
  selected: IUserVim[] = [];
  removing: IUserVim[] = [];
  selectedUsers: IUserVim[] = [];
  roles: IRole[] = VIM_ROLES;
  vimId: string;

  constructor(private vimUserService: UserVimService, private messageService: MessageService, private activatedRoute: ActivatedRoute) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
  }

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.vimUserService.get(this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.users = this.sortUsers(res.body.data);
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }
  addUser() {
    this.selected.forEach(user => {
      this.users.splice(this.users.indexOf(user), 1);
    });
    this.selectedUsers = this.selectedUsers.concat(this.selected);
  }

  removeUser() {
    this.removing.forEach(user => {
      this.selectedUsers.splice(this.selectedUsers.indexOf(user), 1);
    });
    this.users = this.sortUsers(this.users.concat(this.removing));
  }

  addAll() {
    this.selectedUsers = this.users;
    this.users = [];
  }

  removeAll() {
    this.users = this.sortUsers(this.selectedUsers);
    this.selectedUsers = [];
  }

  sortUsers(userArr: IUserVim[]) {
    return userArr.sort((a, b) => {
      return a.username.localeCompare(b.username);
    });
  }
}
