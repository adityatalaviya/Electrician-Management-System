import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  let token = null;

  // ✅ detect route type
  if (req.url.includes('/admin')) {
    token = localStorage.getItem('token'); // admin token
  } 
  else if (req.url.includes('/electrician')) {
    token = localStorage.getItem('electricianToken');
  } 
  else if (req.url.includes('/worker')) {
    token = localStorage.getItem('workerToken');
  }

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};