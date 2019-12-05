import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AccountService } from 'app/core';
import { Account } from 'app/core/user/account.model';
import { Md5 } from 'ts-md5';
import { GRAVATAR_BASE_URL, GRAVATAR_AVATAR_PATH, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  error: string;
  success: string;
  languages: any[];
  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [false],
    authorities: [[]],
    langKey: ['en'],
    username: [],
    imageUrl: []
  });

  constructor(private accountService: AccountService, private fb: FormBuilder) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.updateForm(account);
    });
  }

  save() {
    const settingsAccount = this.accountFromForm();
    this.accountService.save(settingsAccount).subscribe(
      () => {
        this.error = null;
        this.success = 'OK';
        this.accountService.identity(true).then(account => {
          this.updateForm(account);
        });
      },
      () => {
        this.success = null;
        this.error = 'ERROR';
      }
    );
  }

  private accountFromForm(): any {
    const account = {};
    return {
      ...account,
      firstName: this.settingsForm.get('firstName').value,
      lastName: this.settingsForm.get('lastName').value,
      email: this.settingsForm.get('email').value,
      activated: this.settingsForm.get('activated').value,
      authorities: this.settingsForm.get('authorities').value,
      langKey: this.settingsForm.get('langKey').value,
      username: this.settingsForm.get('username').value,
      imageUrl: this.settingsForm.get('imageUrl').value
    };
  }

  updateForm(account: any): void {
    this.settingsForm.patchValue({
      firstName: account.firstName,
      lastName: account.lastName,
      email: account.email,
      activated: account.activated,
      authorities: account.authorities,
      langKey: account.langKey,
      username: account.username,
      imageUrl: account.imageUrl
    });
  }

  getGravatarImageURL() {
    const email = this.settingsForm.get('email').value || '';
    const hash = Md5.hashStr(email.trim().toLowerCase());
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + hash + GRAVATAR_PARAMETERS;
  }
}
