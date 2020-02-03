import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';

import { AccountService, TierType } from 'app/core';
import { Account } from 'app/core/user/account.model';
import { GRAVATAR_BASE_URL, GRAVATAR_AVATAR_PATH, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  account: Account;
  tierType = TierType;
  ngbModalRef: NgbModalRef;
  @ViewChild('cancelTemplate', { static: true }) cancelTemplate: TemplateRef<any>;
  @ViewChild('changeTemplate', { static: true }) changeTemplate: TemplateRef<any>;

  constructor(private accountService: AccountService, private modalService: NgbModal) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.account = account;
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  getGravatarImageURL() {
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + (this.account ? this.account.imageUrl : '') + GRAVATAR_PARAMETERS;
  }

  cancelSubscription() {
    this.ngbModalRef = this.modalService.open(this.cancelTemplate, { size: 'lg', backdrop: 'static' });
    this.ngbModalRef.result.then(
      result => {
        this.accountService.cancelSubscription(result ? result : '').subscribe(res => {
          this.accountService.identity(true);
        });
        this.ngbModalRef = null;
      },
      reason => {
        this.ngbModalRef = null;
      }
    );
  }

  reviseSubscription(tier: TierType) {
    this.ngbModalRef = this.modalService.open(this.changeTemplate, { size: 'lg', backdrop: 'static' });
    this.ngbModalRef.result.then(
      result => {
        this.accountService.reviseSubscription(tier).subscribe(res => {
          this.accountService.identity(true);
        });
        this.ngbModalRef = null;
      },
      reason => {
        this.ngbModalRef = null;
      }
    );
  }

  clear() {
    this.ngbModalRef.dismiss();
  }
}
