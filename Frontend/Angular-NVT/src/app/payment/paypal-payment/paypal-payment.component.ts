import {Component, Input, OnInit} from '@angular/core';
import {ICreateOrderRequest, IPayPalConfig} from 'ngx-paypal';
import {TokenStorageService} from '../../security/services/token-storage/token-storage.service';

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
  @Input() public message = '';

  constructor(private token: TokenStorageService) {
  }

  ngOnInit() {
    this.initConfig([]);
  }

  public reload(ticketIds) {
    console.log('updated payment data');
    this.initConfig(ticketIds);
  }

  private initConfig(ticketsIds): void {
    if (ticketsIds == undefined || ticketsIds.length == 0) {
      this.payPalConfig = undefined;
      return;
    }

    this.payPalConfig = {
      currency: 'EUR',
      clientId: 'AZy7G54GLeTshw95Qw4OIKWgIVptG4dXgVDMDSV-8K-QkPsAHJsc_CvvzZeBcERZQIjdEbgdlL6v2QBu',
      // createOrderOnClient: (data) => <ICreateOrderRequest> {
      //   clientId: 'AZy7G54GLeTshw95Qw4OIKWgIVptG4dXgVDMDSV-8K-QkPsAHJsc_CvvzZeBcERZQIjdEbgdlL6v2QBu',
      //   intent: 'AUTHORIZE',
      //   purchase_units: [
      //     {
      //       amount: {
      //         currency_code: 'EUR',
      //         value: totalPrice,
      //         breakdown: {
      //           item_total: {
      //             currency_code: 'EUR',
      //             value: totalPrice
      //           }
      //         }
      //       },
      //       items: [
      //         {
      //           name: 'Tickets',
      //           quantity: '1',
      //           category: 'DIGITAL_GOODS',
      //           unit_amount: {
      //             currency_code: 'EUR',
      //             value: totalPrice
      //           },
      //         }
      //       ]
      //     }
      //   ]
      // },
      advanced: {
        commit: 'true',
        // extraQueryParams: [
        //   {
        //     name: 'intent',
        //     value: 'authorize'
        //   }
        // ]
      },
      style: {
        label: 'paypal',
        layout: 'vertical'
      },
      createOrderOnServer: (data) => fetch('http://localhost:8080/payment/create-paypal-transaction', {
        method: 'post',
        mode: 'cors',
        headers: {
          'authorization' : 'Bearer ' + this.token.getToken(),
          'content-type': 'application/json'
        },
        body: JSON.stringify({
          ticketIds: ticketsIds
        })
      })
        .then((res) => res.json())
        .then((order) => order.orderID),
      authorizeOnServer: (approveData) => fetch('http://localhost:8080/payment/authorize-paypal-transaction', {
          method: 'post',
          mode: 'cors',
          headers: {
            'authorization' : 'Bearer ' + this.token.getToken(),
            'content-type': 'application/json'
          },
          body: JSON.stringify({
            orderID: approveData.orderID
          })
        }).then((res) => {
          console.log(res);
          return res.json();
        }).then((details) => {
          alert('Payment successful!');
          location.reload();
        }),
      onApprove: (data, actions) => {
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        actions.order.get().then(details => {
          console.log('onApprove - you can get full order details inside onApprove: ', details);
        });
      },
      onCancel: (data, actions) => {
        console.log('OnCancel', data, actions);
      },
      onError: err => {
        console.log('OnError', err);
        alert('Paymen failed:\n' + err);
      },
      onClick: (data, actions) => {
        console.log('onClick', data, actions);
      },
    };
  }
}
