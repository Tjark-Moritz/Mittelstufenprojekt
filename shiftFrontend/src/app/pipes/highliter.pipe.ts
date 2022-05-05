import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'highlighter'
})
export class HighliterPipe implements PipeTransform {

  transform(value: any, args: any,type:string) {
    if(!args) return value;
    if(type=='full'){
      const re = new RegExp("\\b("+args+"\\b)", 'igm');
      value= value.replace(re, '<mark class="cMark">$1</mark>');
    }
    else{
      const re = new RegExp(args, 'igm');
      value= value.replace(re, "<mark class='cMark'>$&</mark>");
    }
    return value;
  }


}
