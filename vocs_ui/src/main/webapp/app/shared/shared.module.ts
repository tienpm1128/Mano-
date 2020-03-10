import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { VOcsSharedCommonModule, HasAnyAuthorityDirective } from './';
import { BlockCopyPasteDirective } from 'app/shared/blockCopyPaste/blockCopyPaste.directive';

@NgModule({
  imports: [VOcsSharedCommonModule],
  declarations: [HasAnyAuthorityDirective, BlockCopyPasteDirective],
  entryComponents: [],
  exports: [VOcsSharedCommonModule, HasAnyAuthorityDirective, BlockCopyPasteDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VOcsSharedModule {
  static forRoot() {
    return {
      ngModule: VOcsSharedModule
    };
  }
}
