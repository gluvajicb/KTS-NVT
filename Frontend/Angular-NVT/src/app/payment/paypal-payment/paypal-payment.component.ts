import {Component, OnInit} from '@angular/core';
import {ICreateOrderRequest, IPayPalConfig} from 'ngx-paypal';

@Component({
  selector: 'app-paypal-payment',
  templateUrl: './paypal-payment.component.html',
  styleUrls: ['./paypal-payment.component.css']
})
export class PaypalPaymentComponent implements OnInit {
  public payPalConfig?: IPayPalConfig;
  public showSuccess = false;
  public showCancel = false;
  public showError = false;

  constructor() {
  }

  ngOnInit() {
    this.initConfig();
  }

  private initConfig(): void {
    this.payPalConfig = {
      currency: 'EUR',
      clientId: 'AZy7G54GLeTshw95Qw4OIKWgIVptG4dXgVDMDSV-8K-QkPsAHJsc_CvvzZeBcERZQIjdEbgdlL6v2QBu',
      createOrderOnClient: (data) => <ICreateOrderRequest> {
        clientId: 'AZy7G54GLeTshw95Qw4OIKWgIVptG4dXgVDMDSV-8K-QkPsAHJsc_CvvzZeBcERZQIjdEbgdlL6v2QBu',
        intent: 'AUTHORIZE',
        purchase_units: [
          {
            amount: {
              currency_code: 'EUR',
              value: '9.99',
              breakdown: {
                item_total: {
                  currency_code: 'EUR',
                  value: '9.99'
                }
              }
            },
            items: [
              {
                name: 'Enterprise Subscription',
                quantity: '1',
                category: 'DIGITAL_GOODS',
                unit_amount: {
                  currency_code: 'EUR',
                  value: '9.99',
                },
              }
            ]
          }
        ]
      },
      advanced: {
        commit: 'true',
        extraQueryParams: [
          {
            name: 'intent',
            value: 'authorize'
          }
        ]
      },
      style: {
        label: 'paypal',
        layout: 'vertical'
      },
      authorizeOnServer: (approveData) => fetch('http://localhost:8080/security/authorize-paypal-transaction', {
          method: 'post',
          mode: 'cors',
          headers: {
            'content-type': 'application/json'
          },
          body: JSON.stringify({
            orderID: approveData.orderID
          })
        }).then((res) => {
          console.log(res);
          return res.json();
        }).then((details) => {
          console.log(details);
        }),
      onApprove: (data, actions) => {
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        actions.order.get().then(details => {
          console.log('onApprove - you can get full order details inside onApprove: ', details);
        });
      },
      onClientAuthorization: (data) => {
        console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
        this.showSuccess = true;
      },
      onCancel: (data, actions) => {
        console.log('OnCancel', data, actions);
      },
      onError: err => {
        console.log('OnError', err);
      },
      onClick: (data, actions) => {
        console.log('onClick', data, actions);
      },
    };
  }
}
