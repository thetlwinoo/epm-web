import { IPhotos } from 'app/shared/model/photos.model';

export interface IProductCategory {
  id?: number;
  name?: string;
  shortLabel?: string;
  thumbnailUrl?: string;
  photoLists?: IPhotos[];
  parentName?: string;
  parentId?: number;
}

export class ProductCategory implements IProductCategory {
  constructor(
    public id?: number,
    public name?: string,
    public shortLabel?: string,
    public thumbnailUrl?: string,
    public photoLists?: IPhotos[],
    public parentName?: string,
    public parentId?: number
  ) {}
}
