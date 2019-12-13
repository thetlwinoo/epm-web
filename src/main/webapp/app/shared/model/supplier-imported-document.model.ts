import { Moment } from 'moment';

export interface ISupplierImportedDocument {
  id?: number;
  importedTemplateContentType?: string;
  importedTemplate?: any;
  importedFailedTemplateContentType?: string;
  importedFailedTemplate?: any;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  uploadTransactionId?: number;
}

export class SupplierImportedDocument implements ISupplierImportedDocument {
  constructor(
    public id?: number,
    public importedTemplateContentType?: string,
    public importedTemplate?: any,
    public importedFailedTemplateContentType?: string,
    public importedFailedTemplate?: any,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public uploadTransactionId?: number
  ) {}
}
